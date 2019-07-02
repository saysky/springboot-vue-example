<template>
    <div class="main-login">
        <lyz-layout
                :pagination="pagination"
                :label="label"
                @handleSizeChange="handleSizeChange"
                @handleCurrentChange="handleCurrentChange">
            <div slot="main" class="main-body">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="未读消息" name="unread"></el-tab-pane>
                    <el-tab-pane label="已读消息" name="read"></el-tab-pane>
                    <el-tab-pane label="回收站" name="deleted"></el-tab-pane>
                </el-tabs>
                <el-table
                        :data="tableData"
                        v-loading="loginLoading"
                        height="90%"
                        style="width: 100%">
                    <el-table-column
                            v-for="(data,index) in tableHeader"
                            :key="index"
                            :prop="data.prop"
                            :label="data.label"
                            :min-width="data['min-width']"
                            :align="data.align">
                    </el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            min-width="120">
                        <template slot-scope="scope">
                            <el-button size="mini"
                                       v-if="scope.row.status === 'unread'"
                                       @click="updateStatus(scope.row.id,'read')">标为已读
                            </el-button>
                            <el-button type="danger" size="mini"
                                       v-if="scope.row.status === 'read'"
                                       @click="updateStatus(scope.row.id,'deleted')">删除
                            </el-button>
                            <el-button type="danger" size="mini"
                                       v-if="scope.row.status === 'deleted'"
                                       @click="deleteMessage(scope.row.id)">永久删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

        </lyz-layout>
    </div>
</template>

<script>
    import lyzLayout from '@/components/lyzLayout';
    import manage from '../manage.component';
    import {formatDateTime, responseText, debounce} from '../../../config/utils.js';

    export default {
        name: "message",
        data() {
            return {
                activeName: 'unread',
                pagination: {
                    pageIndex: 1,
                    pageSize: 10,
                    total: 0,
                },
                label: '系统消息',
                tableData: [],
                loginLoading: false,
                tableHeader: [
                    {
                        prop: 'content',
                        label: '内容',
                        'min-width': 200,
                        align: 'left'
                    },
                    {
                        prop: 'createTime',
                        label: '时间',
                        'min-width': 100,
                        align: 'left'
                    },

                ]
            };
        },
        components: {
            lyzLayout,
        },
        mixins: [manage],
        created() {
            this.queryList();
        },
        mounted() {
            this.$watch('queryKeyword', debounce(() => {
                this.pagination.pageIndex = 1;
                this.queryList();
            }, 1000));
        },
        methods: {
            handleClick(tab, event) {
                this.queryList();
            },
            queryList() {
                this.loginLoading = true;
                let params = {
                    status: this.activeName,
                    pageNo: this.pagination.pageIndex,
                    pageSize: this.pagination.pageSize
                };
                this.$http.get('/message/paging', {params: params}).then(({body}) => {
                    console.log(body);
                    if (body.success === true) {
                        responseText(body.data.records).forEach((item) => {
                            if (item.createTime != null) {
                                item.createTime = new Date(item.createTime).toLocaleString().replace(/:\d{1,2}$/, ' ');
                            }
                        })
                        this.tableData = responseText(body.data.records);
                        this.pagination.total = body.data.records ? body.data.total : 0;
                    } else {
                        this.$message.error(body.message);
                    }
                }).finally(() => {
                    this.loginLoading = false;
                })
            },
            updateStatus(id, status) {
                this.$http.post("/message/updateStatus?id=" + id + "&status=" + status, {}, {}).then(({body}) => {
                    if (body.success === true) {
                        this.queryList();
                        this.$message.success(body.message);
                        window.location.reload();
                    } else {
                        this.$message.error(body.message);
                    }
                }).catch(() => {
                    this.$message.error('删除失败');
                })
            },
            deleteMessage(id) {
                this.$http.post("/message/delete?id=" + id, {}, {}).then(({body}) => {
                    if (body.success === true) {
                        this.queryList();
                        this.$message.success(body.message);
                    } else {
                        this.$message.error(body.message);
                    }
                }).catch(() => {
                    this.$message.error('删除失败');
                })
            },
        }
    }
</script>

<style scoped>
    .main-login {
        height: 100%;
    }

    .danger-text {
        color: #F56C6C;
    }

    .text-blue {
        color: blue;
    }
</style>
